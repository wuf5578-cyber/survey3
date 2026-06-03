package io.cug.modules.gom.controller;

import io.cug.common.utils.DateUtil;
import io.cug.common.utils.R;
import io.cug.modules.admin.entity.AppUserEntity;
import io.cug.modules.app.annotation.Login;
import io.cug.modules.app.annotation.LoginUser;
import io.cug.modules.gom.entity.*;
import io.cug.modules.gom.param.ComputeInfo;
import io.cug.modules.gom.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.*;

//观测数据-水平角、竖角、斜距
class ObserveValue{
    long nObjID;
    double hAngle, vAngle, distance; //hAngle - X, vAngle - Y, distance - Z
}

class MyCompareByID implements Comparator<ObserveValue> {
    @Override
    public int compare(ObserveValue o1, ObserveValue o2){
        return (int) o1.nObjID - (int) o2.nObjID;
    }
}

//水平角比较大小
class MyCompareByHAngle implements Comparator<ObserveValue> {
    @Override
    public int compare(ObserveValue o1, ObserveValue o2){
        return (int) ( 100000 * (o1.hAngle - o2.hAngle) );
    }
}

//测站
class Station{
    long nID;
    List<ObserveValue> observeValues;

    Station(){
        observeValues = new ArrayList<ObserveValue>();
    }
}

//计算单元
class ComputeCell{
    long nPeriod;
    static final double PI = Math.acos(-1);
    List<Station> stations;
    List<ObserveValue> knownedPoints;

    ComputeCell(){
        knownedPoints = new ArrayList<ObserveValue>();
        stations = new ArrayList<Station>();
    }

    ObserveValue FindPointByID(long nID, List<ObserveValue> points){
        Optional<ObserveValue> opt = points.stream().filter(a -> a.nObjID == nID).findFirst();
        if (opt.isPresent()) {
            return opt.get();
        }

        return null;
    }

    ObserveValue FindKnownPointByID(long nID){
        return FindPointByID(nID, knownedPoints);
    }

    //功能：边角交会计算
    //参数：ptStation-待求点, 已知点（ptOne，ptTwo），全站仪观测值（ovOne， ovTwo）-水平角、竖角、斜距
    int CalStationCoor(ObserveValue ptStation, ObserveValue ptOne, ObserveValue ptTwo, ObserveValue ovOne, ObserveValue ovTwo){

        double s1 = CalHDistance(ovOne );//水平距离
        double s2 = CalHDistance(ovTwo);
        double s = CalDistance(ptOne, ptTwo);//两已知点间水平距离
        double fwj12 = CalAzimuth(ptOne, ptTwo);//1->2方位角

        double a = ovTwo.hAngle - ovOne.hAngle;
        double cosTwoOneStation = (s*s + s1*s1 - s2*s2) / (2 * s * s1);//余玄定理
        double angleTwoOneStation = Math.acos(cosTwoOneStation);

        double aEx = ovTwo.hAngle - ovOne.hAngle;
        double angleTwoOneStationEx = Math.asin(s2 * Math.sin(a) / s);//正玄定理

        if (a < PI){
            fwj12 += angleTwoOneStation;
        }
        else {
            fwj12 += 2 * PI - angleTwoOneStation;
        }

        ptStation.hAngle = ptOne.hAngle + s1 * Math.cos(fwj12);
        ptStation.vAngle = ptOne.vAngle + s1 * Math.sin(fwj12);
        ptStation.distance = ptOne.distance - s1 * Math.tan( CalVAngle(ovOne.vAngle) );

        return 1;
    }

    //功能：边角交会计算
    //参数：ptStation-待求点, 已知点（ptOne，ptTwo），全站仪观测值（ovOne， ovTwo）-水平角、竖角、斜距
    int CalStationCoorEx(ObserveValue ptStation, ObserveValue ptOne, ObserveValue ptTwo, ObserveValue ovOne, ObserveValue ovTwo){
        ObserveValue p1, p2, v1, v2;
        double a = ovTwo.hAngle - ovOne.hAngle;
        if (a < PI){
            p1 = ptTwo; v1 = ovTwo;
            p2 = ptOne; v2 = ovOne;
        }
        else {
            p1 = ptOne; v1 = ovOne;
            p2 = ptTwo; v2 = ovTwo;
        }

        double d1 = CalHDistance(v1);//水平距离
        double d2 = CalHDistance(v2);
        double d = CalDistance(p1, p2);
        double t = (d1*d1 + d*d - d2*d2) / (2*d);
        double h = Math.sqrt(d1*d1 - t*t);
        double dx12 = p2.hAngle - p1.hAngle;
        double dy12 = p2.vAngle - p1.vAngle;

        ptStation.hAngle = p1.hAngle + (t * dx12 + h * dy12) / d;
        ptStation.vAngle = p1.vAngle + (t * dy12 + h * dx12) / d;
        ptStation.distance = p1.distance - d1 * Math.tan( CalVAngle(v1.vAngle) );

        return 1;
    }

    //功能：反求测站
    //参数：ptStation-待求点, 已知点-pts，全站仪观测值-ovs(水平角、竖角、斜距)
    int CalStationCoor(ObserveValue ptStation, List<ObserveValue> pts, List<ObserveValue> ovs) {
        int n = pts.size(), iLast = n / 3;
        if (iLast < 1) iLast = 1;
        if (n < 2){
            return 0;
        }
        else if(n < 3)
        {
            return CalStationCoor(ptStation, pts.get(0), pts.get(iLast), ovs.get(0), ovs.get(iLast));
        }
        else if( n < 4) {
            return CalStationCoorEx(ptStation, pts.get(0), pts.get(iLast), ovs.get(0), ovs.get(iLast));
        }
        else
        {
            ptStation.hAngle = ptStation.vAngle = ptStation.distance = 0;
            for (int i = 0; i < n; i++){
                ptStation.hAngle += pts.get(i).hAngle;
                ptStation.vAngle += pts.get(i).vAngle;
                ptStation.distance += pts.get(i).distance;
            }
            ptStation.hAngle /= n;
            ptStation.vAngle /= n;
            ptStation.distance /= n;

            int kk = 0;
            double dd = 0;
            do {
                dd = CalAdjust(ptStation, n, pts, ovs);
                kk++;
            }while (dd > 0.0001 || kk > 100);

            return 1;
        }
    }

    //功能：三维平差求解坐标 -误差方程（空间距离为观测值）
    double CalAdjust(ObserveValue ptStation, int n, List<ObserveValue> pts, List<ObserveValue> ovs) {
            //1.构建误差方程
            double [][]A = new double[n][3];//系数阵
            double []L = new double[n];//常数项阵
            for (int i = 0; i < n; i++){
                double dx = pts.get(i).hAngle - ptStation.hAngle;
                double dy = pts.get(i).vAngle - ptStation.vAngle;
                double dz = pts.get(i).distance - ptStation.distance;
                double s = Math.sqrt(dx*dx + dy*dy + dz*dz);
                L[i] = s - ovs.get(i).distance;
                A[i][0] = -dx / s;
                A[i][1] = -dy / s;
                A[i][2] = -dz / s;
            }

            //2.系数阵转置
            double [][]AT = new double[3][n];
            for (int i = 0; i < 3; i++){
                for (int j = 0; j < n; j++){
                    AT[i][j] = A[j][i];
                }
            }

            //3.组成法方程
            double [][]N = new double[3][3];
            double []AL = new double[3];
            for (int i = 0; i < 3; i++){
                //系数阵
                for (int j = 0; j < 3; j++){
                    N[i][j] = 0;
                    for (int k = 0; k < n; k++){
                        N[i][j] += AT[i][k] * A[k][j];
                    }
                }

                //常数项
                AL[i] = 0;
                for (int k = 0; k < n; k++){
                    AL[i] += AT[i][k] * L[k];
                }
            }

            //4.解法方程，求改正数
            MatrixInv(N, 3);
            double []XX = new double[3];
            double dSum = 0;
            for (int i = 0; i < 3; i++){
                XX[i] = 0;
                for (int j = 0; j < 3; j++){
                    XX[i] += N[i][j] * AL[j];
                }

                dSum += XX[i] * XX[i];
            }

            //5.合成坐标
            ptStation.hAngle -= XX[0];
            ptStation.vAngle -= XX[1];
            ptStation.distance -= XX[2];

            return Math.sqrt(dSum);
    }

    //功能：逆
    //参数：n---整数，矩阵的阶数,a---n*n二维数组，开始时为原，返回时为逆
    public static void MatrixInv(double[][] a, int n) {
        int i, j, row, col, k;
        double max, temp;
        int[] p = new int[n];
        double[][] b = new double[n][n];
        for (i = 0; i < n; i++) {
            p[i] = i;
            b[i][i] = 1;
        }

        for (k = 0; k < n; k++) {
            // 找主元
            max = 0;
            row = col = i;
            for (i = k; i < n; i++)
                for (j = k; j < n; j++) {
                    temp = Math.abs(b[i][j]);
                    if (max < temp) {
                        max = temp;
                        row = i;
                        col = j;
                    }
                }
            // 交换行列，将主元调整到 k 行 k 列上
            if (row != k) {
                for (j = 0; j < n; j++) {
                    temp = a[row][j];
                    a[row][j] = a[k][j];
                    a[k][j] = temp;
                    temp = b[row][j];
                    b[row][j] = b[k][j];
                    b[k][j] = temp;
                }
                i = p[row];
                p[row] = p[k];
                p[k] = i;
            }
            if (col != k) {
                for (i = 0; i < n; i++) {
                    temp = a[i][col];
                    a[i][col] = a[i][k];
                    a[i][k] = temp;
                }
            }
            // 处理
            for (j = k + 1; j < n; j++)
                a[k][j] /= a[k][k];
            for (j = 0; j < n; j++)
                b[k][j] /= a[k][k];
            a[k][k] = 1;

            for (j = k + 1; j < n; j++) {
                for (i = 0; i < k; i++)
                    a[i][j] -= a[i][k] * a[k][j];
                for (i = k + 1; i < n; i++)
                    a[i][j] -= a[i][k] * a[k][j];
            }
            for (j = 0; j < n; j++) {
                for (i = 0; i < k; i++)
                    b[i][j] -= a[i][k] * b[k][j];
                for (i = k + 1; i < n; i++)
                    b[i][j] -= a[i][k] * b[k][j];
            }
            for (i = 0; i < k; i++)
                a[i][k] = 0;
            a[k][k] = 1;
        }
        // 恢复行列次序；
        for (j = 0; j < n; j++)
            for (i = 0; i < n; i++)
                a[p[i]][j] = b[i][j];
    }

    //功能：计算两点间水平距离
    double CalDistance(ObserveValue p1, ObserveValue p2){
        double dx = p1.hAngle - p2.hAngle;
        double dy = p1.vAngle - p2.vAngle;
        return Math.sqrt(dx * dx + dy * dy);
    }

    //功能：斜距转换成水平距离
    double CalHDistance(ObserveValue ov){
        return ov.distance * Math.cos( CalVAngle( ov.vAngle) );
    }

    //功能：竖盘盘左度数求竖角
    double CalVAngle(double vA){
        return PI / 2 - vA;
    }

    //功能：求方位角
    double	CalAzimuth(ObserveValue p1, ObserveValue p2)
    {
        double Dx = p2.hAngle - p1.hAngle;
        double Dy = p2.vAngle - p1.vAngle;
        double S = Math.sqrt(Dx * Dx + Dy * Dy);
        if (S < 1e-15)	return 0.0;
        double T = Math.asin(Dy / S);
        if (Dx < 0)	T = PI - T;
        if ((Dx > 0 && Dy < 0) || T < 0)
            T = 2 * PI + T;

        return T;
    }
}

@Api(tags = "监测——测量组")
@RestController
@RequestMapping("gom/AutoComputeGroup")
public class AutoComputeGroupController {

    @Autowired
    private AutoComputeGroupService autoComputeGroupService;

    @Autowired
    private AutoComputeService autoComputeService;

    @Autowired
    private AutoMeasureService autoMeasureService;

    @Autowired
    private AutoCoorService autoCoorService;

    @Login
    @PostMapping("/List")
    @ApiOperation("列表")
    public R classList(@LoginUser AppUserEntity user, @RequestBody AutoComputeGroupEntity autoComputeGroupEntity){
        List<AutoComputeGroupEntity> list = autoComputeGroupService.QueryValidComputeGroup(autoComputeGroupEntity.getProjectId());
        for (int i = 0;i<list.size();i++){
            List<AutoComputeEntity> autoComputeEntityList = autoComputeService.QueryValidCompute(list.get(i).getId());
            list.get(i).setAutoComputeEntityList(autoComputeEntityList);
        }
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/add")
    @ApiOperation("增加")
    public R add(@LoginUser AppUserEntity user, @RequestBody AutoComputeGroupEntity autoComputeGroupEntity) {
        autoComputeGroupEntity.setCreateTime(DateUtil.nowDateTime());
        autoComputeGroupService.save(autoComputeGroupEntity);
        List<AutoComputeEntity> list = autoComputeGroupEntity.getAutoComputeEntityList();
        for(int i = 0; i<autoComputeGroupEntity.getCount();i++){
            list.get(i).setGroupId(autoComputeGroupEntity.getId());
            list.get(i).setCreateTime(DateUtil.nowDateTime());
            autoComputeService.save(list.get(i));
        }
        return R.ok().put("result", autoComputeGroupEntity);
    }

    @Login
    @PostMapping("/delete")
    @ApiOperation("删除")
    public R delete(@LoginUser AppUserEntity user, @RequestBody AutoComputeGroupEntity autoComputeGroupEntity){
        List<AutoComputeEntity> list = autoComputeService.QueryValidCompute(autoComputeGroupEntity.getId());
        for(int i = 0;i<list.size();i++){
            autoComputeService.DelComputeByGroupIdAndSurveyIndex(list.get(i).getGroupId(),list.get(i).getSurveyIndex());
        }
        autoComputeGroupEntity.setStatus(99);//99-删除状态
        autoComputeGroupService.updateById(autoComputeGroupEntity);
        return R.ok().put("result", "删除成功!");
    }

    void deleteCoor(AutoCoorEntity autoCoorEntity){
        try {
            autoCoorEntity.setStatus(99);
            autoCoorService.updateByObjectId(autoCoorEntity.getPeriodId(),autoCoorEntity.getObjectId());
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Login
    @PostMapping("/edit")
    @ApiOperation("修改")
    public R edit(@LoginUser AppUserEntity user, @RequestBody AutoComputeGroupEntity autoComputeGroupEntity){
        int count = autoComputeService.QueryValidCompute(autoComputeGroupEntity.getId()).size();
        autoComputeGroupService.updateById(autoComputeGroupEntity);
        int countNew = autoComputeGroupEntity.getCount();
        List<AutoComputeEntity> list = autoComputeGroupEntity.getAutoComputeEntityList();
        if(count == countNew){
            for(AutoComputeEntity entity:list){
                autoComputeService.UpdateComputeByGroupIdAndSurveyIndex(entity.getStationId(),entity.getGroupId(),entity.getSurveyIndex());
            }
        }else if(count > countNew){
            for(int i = 0;i<countNew;i++){
                autoComputeService.UpdateComputeByGroupIdAndSurveyIndex(list.get(i).getStationId(),list.get(i).getGroupId(),list.get(i).getSurveyIndex());
            }
            for(int j = countNew;j<count;j++){
                autoComputeService.DelComputeByGroupIdAndSurveyIndex(list.get(j).getGroupId(),list.get(j).getSurveyIndex());
            }
        }else if(count < countNew){
            for(int i = 0;i<count;i++){
                autoComputeService.UpdateComputeByGroupIdAndSurveyIndex(list.get(i).getStationId(),list.get(i).getGroupId(),list.get(i).getSurveyIndex());
            }
            for(int j = count;j<countNew;j++){
                list.get(j).setCreateTime(DateUtil.nowDateTime());
                autoComputeService.save(list.get(j));
            }
        }
        return R.ok().put("result", "更新成功!");
    }

    @Login
    @PostMapping("/handle")
    @ApiOperation("计算处理")
    public R handle(@LoginUser AppUserEntity user, @RequestBody ComputeInfo computeInfo){
        CalCoor(computeInfo.getProjectId(),computeInfo.getPeriodId(),computeInfo.getComputeGroupId());


        return R.ok().put("result", "计算完毕!");
    }


    //功能：读取已知点数据  -- "工程ID=nProjectID"
    void ReadKnownPoint(long nProjectID, ComputeCell computeCell){
        List<AutoCoorEntity> coorEntityList = autoCoorService.QueryValidGivenPoint(nProjectID);
        for (int k = 0; k < coorEntityList.size(); k++){
            ObserveValue observeValue = new ObserveValue();
            observeValue.nObjID = coorEntityList.get(k).getObjectId();
            observeValue.hAngle = coorEntityList.get(k).getX();
            observeValue.vAngle = coorEntityList.get(k).getY();
            observeValue.distance = coorEntityList.get(k).getH();
            computeCell.knownedPoints.add(observeValue);
        }
    }

    //功能：读取监测数据 -- “周期ID=nPeriodID”
    void ReadStation(long nComputeGroupID, long nPeriodID, ComputeCell computeCell){
        List<AutoComputeEntity> listCompute = autoComputeService.QueryValidCompute(nComputeGroupID);
        for(int i = 0; i<listCompute.size(); i++){
            Station station = new Station();
            station.nID = listCompute.get(i).getStationId();
            List<AutoMeasureEntity> list = autoMeasureService.QueryValidMeasure(station.nID, nPeriodID);
            for (int j = 0; j < list.size(); j++){
                ObserveValue observeValue = new ObserveValue();
                observeValue.nObjID = list.get(j).getObjectId();
                observeValue.hAngle = list.get(j).getHAngle();
                observeValue.vAngle = list.get(j).getVAngle();
                observeValue.distance = list.get(j).getDistance();
                station.observeValues.add(observeValue);
            }
            if (list.size() > 1){//水平角顺时针大小排列
                station.observeValues.sort(new MyCompareByHAngle());
            }

            computeCell.stations.add(station);
        }
    }

    //功能：坐标计算主体部分
    public R CalCoor(long nProjectID, long nPeriodID, long nComputeGroupID){
        ComputeCell computeCell  = new ComputeCell();
        ReadKnownPoint(nProjectID, computeCell);
        ReadStation(nComputeGroupID, nPeriodID, computeCell);

        int nKnownPointCount = computeCell.knownedPoints.size();
        if (nKnownPointCount < 2){
            return R.error().put("计算", "已知点数目少于2个，无法计算坐标!");
        }
        else {
            computeCell.knownedPoints.sort(new MyCompareByID());
        }

        int nStationCount = computeCell.stations.size();
        if (nStationCount < 1){
            return R.error().put("计算", "没有观测数据，无需计算!");
        }

        //计算结果存放的点列表
        List<ObserveValue> points = new ArrayList<ObserveValue>();

        for (int i = 0; i < nStationCount; i++){
            Station sta = computeCell.stations.get(i);
            int nObsCount = sta.observeValues.size();
            List<ObserveValue> pts = new ArrayList<ObserveValue>();//记录本测站的已知点方向
            List<ObserveValue> ovs = new ArrayList<ObserveValue>();

            //1.获取已知方向
            for (int j = 0; j < nObsCount; j++){
                ObserveValue ov = sta.observeValues.get(j);
                ObserveValue pp = computeCell.FindKnownPointByID(ov.nObjID);
                if (pp == null){
                    pp = computeCell.FindPointByID(ov.nObjID, points);//已经计算过的点列表
                }
                if (pp != null){
                    pts.add(pp);
                    ovs.add(ov);
                }
            }

            //2.计算测站点坐标
            int nGivedPointDirectCount = pts.size();
            if (nGivedPointDirectCount < 2 || nGivedPointDirectCount != ovs.size()){
                return R.error().put("计算", "本测站没有2个已知点的观测方向，无需计算!");
            }

            ObserveValue ptStation = new ObserveValue();//测站点坐标
            computeCell.CalStationCoor(ptStation, pts, ovs);

            //测站虚拟零方向的方位角
            double fwj = 0;
            for (int k = 0; k < nGivedPointDirectCount; k++){
                double aa = computeCell.CalAzimuth(ptStation, pts.get(k)) + ovs.get(k).hAngle;
                if(aa >= computeCell.PI * 2) aa -= computeCell.PI * 2;
                fwj += aa;
            }
            fwj /= nGivedPointDirectCount;

            //3.计算 监测点 的坐标
            for (int k, j = 0; j < nObsCount; j++){
                ObserveValue ov = sta.observeValues.get(j);

                //已知点或计算过的点，不重复计算
                for (k = 0; k < nGivedPointDirectCount; k++){
                    if (ov.nObjID == ovs.get(k).nObjID) break;
                }
                if (k != nGivedPointDirectCount) continue;

                double s = computeCell.CalHDistance(ov);//水平距离
                double a = fwj - ov.hAngle;
                ObserveValue pt = new ObserveValue();
                pt.nObjID = ov.nObjID;
                pt.hAngle = ptStation.hAngle + s * Math.cos(a);
                pt.vAngle = ptStation.vAngle + s * Math.sin(a);
                pt.distance = ptStation.distance + s * Math.tan( computeCell.CalVAngle(ov.vAngle) );
                points.add(pt);
                points.sort(new MyCompareByID());
            }

        }

        //将数据库中的计算点数据 (projectId、 periodId、 objectId) status设置为99
        for(int index = 0; index < points.size(); index++) {
            AutoCoorEntity autoCoorEntity = new AutoCoorEntity();
            autoCoorEntity.setPeriodId(nPeriodID);
            autoCoorEntity.setObjectId(points.get(index).nObjID);
            deleteCoor(autoCoorEntity);
        }
        //4.更新数据库表 gom_auto_coor ()
        for (int k = 0; k < points.size(); k++){
            AutoCoorEntity autoCoorEntity = new AutoCoorEntity();
            autoCoorEntity.setProjectId(nProjectID);
            autoCoorEntity.setPeriodId(nPeriodID);
            autoCoorEntity.setObjectId(points.get(k).nObjID);
            autoCoorEntity.setX(points.get(k).hAngle);
            autoCoorEntity.setY(points.get(k).vAngle);
            autoCoorEntity.setH(points.get(k).distance);
            autoCoorEntity.setStatus(0);
            autoCoorEntity.setCreateTime(DateUtil.nowDateTime());

            //插入或更新数据库
            if (autoCoorService.saveOrUpdate(autoCoorEntity)) {
            }
            else {
                return R.error("添加失败!");
            }
        }

        return R.ok().put("result", "计算完毕!");
    }
}
