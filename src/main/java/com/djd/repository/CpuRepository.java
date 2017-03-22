package com.djd.repository;


import com.djd.model.CpuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dzkan on 2016/3/8.
 */
@Repository
public interface CpuRepository extends JpaRepository<CpuEntity, Integer> {

    @Modifying      // 说明该方法是修改操作
    @Transactional  // 说明该方法是事务性操作
    // 定义查询
    // @Param注解用于提取参数
    @Query("update CpuEntity us set us.rate=:qRate, us.timestamp=:qTimestamp where us.id=:qId")
    public void updateCpu(@Param("qRate") String rate, @Param("qTimestamp") String timestamp, @Param("qId") Integer id);


}
