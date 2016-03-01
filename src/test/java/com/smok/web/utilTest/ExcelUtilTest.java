package com.smok.web.utilTest;

import com.smok.web.model.BaseModel;
import com.smok.web.utils.ExcelUtil;
import org.apache.poi.hssf.record.IterationRecord;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by liuaifen on 2016/3/1.
 */
public class ExcelUtilTest {

    @Test
    public void test(){
        List<BaseModel> list1 = new ArrayList<BaseModel>();
        list1.add(new BaseModel("test1","aaa"));
        list1.add(new BaseModel("test2","Bbb"));
        list1.add(new BaseModel("test3","cCC"));

        List<BaseModel> list2 = new ArrayList<BaseModel>();
        list2.add(new BaseModel("test1","aaa"));
        list2.add(new BaseModel("test2","Bbb"));
        list2.add(new BaseModel("test3","cCC"));

        try {
            Workbook wb1 = ExcelUtil.createWorkbook(list1);
            Workbook wb2 = ExcelUtil.createWorkbook(list2);
            String folder = "d:\\tmp\\";
            System.out.println(ExcelUtil.createFile(wb1, folder, "wb1.xlsx"));
            System.out.println(ExcelUtil.createFile(wb2, folder, "wb2.xlsx"));

            Sheet st1 = wb1.getSheetAt(0);
            Sheet st2  = wb2.getSheetAt(0);
            Workbook workbook = ExcelUtil.combineSheet(st1, st2);

            System.out.println(ExcelUtil.createFile(workbook, folder, "combineExcel.xlsx"));


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
