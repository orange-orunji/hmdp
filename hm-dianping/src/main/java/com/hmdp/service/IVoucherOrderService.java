package com.hmdp.service;

import com.hmdp.entity.VoucherOrder;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IVoucherOrderService extends IService<VoucherOrder> {

    Object seckillVoucher(Long voucherId) throws InterruptedException;

    void getVoucherOrder(VoucherOrder voucher);
}
