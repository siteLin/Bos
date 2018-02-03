package com.itheima.bos.dao.base.impl;

import static org.junit.Assert.*;

import java.util.List;

import javax.sound.midi.VoiceStatus;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.itheima.bos.dao.base.StandardRepository;
import com.itheima.bos.domain.base.Standard;

/**  
 * ClassName:StandardRepasitoryTest <br/>  
 * Function:  <br/>  
 * Date:     2018年1月14日 下午4:05:46 <br/>       
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class StandardRepasitoryTest {

    //注入对象
    @Autowired
    private StandardRepository standardRepasitory;
    
    @Test
    public void test() {
        List<Standard> list = standardRepasitory.findAll();
        for (Standard standard : list) {
            System.out.println(standard);
        }
    }
    
    @Test
    public void testadd() {
        Standard standard = new Standard();
        standard.setMaxLength(200);
        standard.setName("吴彦祖3");
        standardRepasitory.save(standard);
    }
    
    @Test// 更具id查找Standard
    public void testFindById() {
        Standard standard = standardRepasitory.findOne(1L);
        System.out.println(standard);
        //return standard;
    }
    
    // 修改1主键的ManLeangth
    @Test
    public void testUpdate() {
       /* Standard standard = standardRepasitory.findOne(1L);
        standard.setMaxLength(10);*/
        Standard standard = new Standard();
        standard.setMaxLength(20);
        standard.setId(1L);
        standardRepasitory.save(standard);
    }

   //根据id删除Standar表记录
    @Test
    public void testDelete() {
        standardRepasitory.delete(30L);
    }
    
    @Test
    public void testFindByName() {
        List<Standard> list = standardRepasitory.testFindByName("吴彦祖3");
        for (Standard standard : list) {
            System.out.println(standard);
        }
    }
    
    @Test // // 根据用户名和最大长度进行查询
    public void testFindByNameAndMaxtleagth() {
        List<Standard> list = standardRepasitory.testFindByNameAndMaxtleagth("吴彦祖3", 200);
        for (Standard standard : list) {
            System.out.println(standard);
        }
    }
    
    
    @Test
    public void deleteByName() {
        standardRepasitory.deleteByName("吴彦祖1");
    }
    
    @Test
    public void updateMaxlengthByName() {
        standardRepasitory.updateMaxlengthByName(3, "吴彦祖");
    }
    
}
  
