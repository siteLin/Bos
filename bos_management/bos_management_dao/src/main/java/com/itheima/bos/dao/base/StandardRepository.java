package com.itheima.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.domain.base.Standard;


/**  
 * ClassName:ArchiveRepasitory <br/>  
 * Function:  <br/>  
 * Date:     2018年1月14日 下午3:54:55 <br/>       
 */
// 泛型 T：操作数据库中对应的实体类 Archive
// 泛型 Serializable：操作数据库中对应的实体类主键的类型Long
public interface StandardRepository extends JpaRepository<Standard, Long> {
    @Query("from Standard where name = ?")
    List<Standard> testFindByName(String name);
    
    // 根据用户名和最大长度进行查询
    @Query("from Standard where name = ? and maxWeight = ?")
    List<Standard> testFindByNameAndMaxtleagth(String name, int i);
    
    // 根据用户名和最大长度进行查询
    @Query("from Standard where name = ?2 and maxLength = ?1") // JPQL === HQL
    List<Standard> testFindByNameAndMaxtleagth2(Integer maxLength, String name);

    // 根据用户名和最大长度进行查询
    @Query(value = "select * from T_STANDARD where C_NAME = ? and C_MAX_LENGTH = ?", nativeQuery = true) // JPQL ===
    List<Standard> testFindByNameAndMaxtleagth3(String name, Integer maxLength);

    @Transactional
    @Modifying
    @Query("delete from Standard where name = ?")
    void deleteByName(String name);
    
    // 自定义根据name修改maxlength
    @Transactional
    @Modifying
    @Query("update Standard set maxLength = ? where name = ?")
    void updateMaxlengthByName(Integer maxLength, String name);
    
}
  
