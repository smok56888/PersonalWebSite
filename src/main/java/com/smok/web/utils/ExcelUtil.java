package com.smok.web.utils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by smok on 2016/1/5.
 */
public class ExcelUtil {

    /**
     * 根据数据列表及制定输出的列名，生成excel
     *
     * @param dataList    数据列表
     * @param columnNames 知道列名的 属性名-展示列名
     * @param <T>         泛型类
     * @return
     * @throws Exception
     */
    public static <T> Workbook createWorkbook(List<T> dataList, Map<String, String> columnNames) throws Exception {
        if (dataList.isEmpty() || columnNames.isEmpty()) {
            return null;
        }
        T t = dataList.get(0);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("sheet-" + t.getClass().getSimpleName());
        Row row0 = sheet.createRow(0);
        Iterator<String> iter = columnNames.keySet().iterator();
        int index = 0;
        while (iter.hasNext()) {
            String key = iter.next();
            row0.createCell(index++, Cell.CELL_TYPE_STRING).setCellValue(columnNames.get(key));
        }

        Field[] fields = t.getClass().getDeclaredFields();
        for (int i = 0; i < dataList.size(); i++) {
            t = dataList.get(i);
            Row row = sheet.createRow(i + 1);
            index = 0;
            for (int j = 0; j < fields.length; j++) {
                Field field = fields[j];
                if (columnNames.containsKey(field.getName())) { //匹配列名
                    field.setAccessible(true);
                    String fieldType = field.getType().getSimpleName();
                    if (fieldType.equals("Date")) { // 针对特殊类型做处理
                        row.createCell(index++, Cell.CELL_TYPE_STRING).setCellValue(DateUtil.formatDateTime((Date) field.get(t)));
                    } else {
                        row.createCell(index++, Cell.CELL_TYPE_STRING).setCellValue(String.valueOf(field.get(t)));
                    }
                }
            }
        }
        return workbook;
    }


    /**
     * 根据数量列表生成excel_workbook
     *
     * @param dataList 数据列表
     * @param <T>      泛型类
     * @return
     * @throws Exception
     */
    public static <T> Workbook createWorkbook(List<T> dataList) throws Exception {
        if (CollectionUtils.isEmpty(dataList)) {
            return null;
        }
        T t = dataList.get(0);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("sheet-" + t.getClass().getSimpleName());
        Row row0 = sheet.createRow(0);
        Field[] fields = t.getClass().getDeclaredFields(); // 获取对象所有属性 -- getFields获取public的属性
        int columnSize = fields.length;
        for (int index = 0; index < columnSize; index++) {
            row0.createCell(index, Cell.CELL_TYPE_STRING).setCellValue(fields[index].getName());
        }

        for (int i = 0; i < dataList.size(); i++) {
            t = dataList.get(i);
            Row row = sheet.createRow(i + 1);
            for (int index = 0; index < columnSize; index++) {
                Field field = fields[index];
                field.setAccessible(true);
                String fieldType = field.getType().getSimpleName();
                if (fieldType.equals("Date")) { // 针对特殊类型做处理
                    row.createCell(index, Cell.CELL_TYPE_STRING).setCellValue(DateUtil.formatDateTime((Date) field.get(t)));
                } else {
                    row.createCell(index, Cell.CELL_TYPE_STRING).setCellValue(String.valueOf(field.get(t)));
                }
            }
        }
        return workbook;
    }

    /**
     * 创建文件
     *
     * @param workbook   excel数据
     * @param folderPath 文件夹 最后一定要有路径分隔符
     * @param fileName   文件名字
     * @return
     * @throws Exception
     */
    public static String createFile(Workbook workbook, String folderPath, String fileName) throws Exception {
        File fileDir = new File(folderPath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        String targetPath = folderPath + fileName;
        File file = new File(targetPath);
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        workbook.write(fos);
        fos.flush();
        fos.close();

        return targetPath;
    }
}
