package team.qucikexcel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by lzf on 2017/8/7.
 */
public class QuickExcel{

    /**
     * 导出excel文件
     * @param pathAndFileName
     * @param className
     * @param listData
     */
    public static void exportExcel(String pathAndFileName, String className, List<?> listData){
        String[] regx = className.split("\\.");
        //创建工作簿
        Workbook workbook = new HSSFWorkbook();
        //创建表
        Sheet sheet = workbook.createSheet(regx[regx.length-1]);
        //创建行
        Row row = sheet.createRow(0);
        row.setHeight((short) (30 * 10));
        List<String>  propertyList = ParseUtil.getUpperProperties(className);
        //创建单元格
        for (int i=0; i<propertyList.size(); i++){
            row.createCell(i).setCellValue(propertyList.get(i));
        }
        String regData = listData.toString().replaceAll("\\[","").replaceAll("\\]","");
        //最终写入excel数据,获取行数据
        String[] data = regData.split(regx[regx.length-1]);
        //获取字段属性
        List<String> properties = ParseUtil.getProperties(className);
        //写入数据
        for (int i=1; i <= data.length; i++){
            if(i == 1){
                continue;
            }
            //创建数据行
            Row rows = sheet.createRow(i-1);
            String[] cellData = data[i-1].split("\\,");
            for (int j=0; j< properties.size(); j++){
                String property = properties.get(j).toString();
                String finalDate = cellData[j].toString().replaceAll(property+"=","").replaceAll("\'","");
                if(j==0){
                    finalDate = finalDate.replace("{","");
                }
                if(j==properties.size()-1){
                    finalDate = finalDate.replace("}","");
                }
                if(finalDate == null || finalDate.trim().equals("null")){
                    finalDate = "";
                }
                rows.createCell(j).setCellValue(finalDate);
            }
        }

        //输出.xls文件
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(new File(pathAndFileName));
            workbook.write(fOut);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
