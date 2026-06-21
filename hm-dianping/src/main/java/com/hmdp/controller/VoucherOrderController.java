package com.hmdp.controller;


import com.hmdp.dto.Result;
import com.hmdp.entity.VoucherOrder;
import com.hmdp.service.IVoucherOrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@RestController
@RequestMapping("/voucher-order")
public class VoucherOrderController {
    @Resource
    private IVoucherOrderService voucherOrderService;

    /**
     * 秒杀优惠券
     * @param voucherId
     * @return
     */
    @PostMapping("seckill/{id}")
    public Result seckillVoucher(@PathVariable("id") Long voucherId) throws InterruptedException {
        Object result = voucherOrderService.seckillVoucher(voucherId);
        if (result instanceof Result) {
            return (Result) result;
        }
        return Result.ok(result);
    }

    /**
     * 轮询接口，查询当前订单是否完成秒杀
     * @param orderId
     * @return
     * 前端轮询逻辑：
     *   GET /voucher-order/{orderId}
     *     ├─ 返回 null         → "处理中，继续等" → 1 秒后再查
     *     ├─ status = 1        → "下单成功！" → 停止轮询
     *     ├─ status = 4        → "下单失败" → 停止轮询
     *     └─ status = 2/3/5/6  → 对应展示
     */
    @GetMapping("/{orderId}")
    public Result queryOrder(@PathVariable Long orderId){
        VoucherOrder order = voucherOrderService.getById(orderId);
    if (order == null) {
        // 订单不存在或处理失败（被死信吃掉），直接返回失败状态码
        return Result.fail("订单处理失败");
    }
    return Result.ok(order.getStatus());
    }
}
