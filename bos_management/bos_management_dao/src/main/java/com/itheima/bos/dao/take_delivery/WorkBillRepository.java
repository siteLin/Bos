package com.itheima.bos.dao.take_delivery;

import com.itheima.bos.domain.take_delivery.WorkBill;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 工单
 */
public interface WorkBillRepository extends JpaRepository<WorkBill,Long> {
}
