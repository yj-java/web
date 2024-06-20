package org.yj.web.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 秒杀
 */
public class SecKill {

    /**
     * 保证不超卖：
     * 1、加锁
     * 1.1：单机情况下加锁可以使用synchronized(monitor:对象头MarkWord、owner)或者reentrantLock(CAS,AQS:state)
     * 1.2：业务集群情况下可以使用分布式锁，比如基于redis的setnx(setIfAbsent，redis自带原子性操作)来加锁
     * 1.2.1：加锁之后可能会存在redis服务挂掉导致锁一直不释放，从而产生死锁的问题，所以需要给key设置过期时间，setnx和过期时间是两步操作，不能保证原子性，所以需要lua脚本来保证原子性
     * 1.2.2：设置过期时间之后，可能业务逻辑执行时间会超过过期时间，导致锁失效，所以需要给锁续期，从而引入看门狗机制，通过定时任务线程池持续的监控线程是否持有锁，持有则重新设置过期时间（续期）
     * 1.2.3：redission实现了上述逻辑，可以直接使用，getlock,unlock
     * 1.2.4：redis单机情况下redission可以锁成功，但是redis集群情况下，主从同步的时候可能会因为主库挂掉之前没来得及同步锁信息而导致锁失效，所以需要引入redlock，需要一半以上redis实例
     * 确认加锁成功，才认为加锁成功，否则加锁失败
     * 2、不加锁：
     * 利用数据库更新的原子性，更新库存的时候加上一个判断条件，库存大于0才能减库存，从而保证不超卖
     * 提升性能：锁之前过滤掉大部分请求，降低数据库压力
     * 1、秒杀前预热，通过定时任务，查询秒杀商品信息，缓存入redis作为热点数据
     * 2、jvm中存放一个currentHashMap存放已经买完了的商品，一进到方法里面就查，查到直接返回，集群模式下也不影响，只是辅助手段
     * 3、redis缓存库存数据(@Cacheable)，扣减库存时删除数据（@CacheEvict)
     * 4、redis记录秒杀商品数量，做减减操作(increment)，低于0的直接返回
     * 5、减小锁的力度，先查询库存是否足够，进行秒杀条件判断（是否在秒杀时间内），只锁住后续操作：再次查询mysql库存是否有剩余库存，扣减库存
     * 6、通过servlet 3.0新特性异步请求增加请求吞吐量
     * 7、通过rocketmq异步下单，websocket通知
     * 8、异步下单，异步通知会因为失败重试机制产生幂等性问题
     * 8.1：数据库唯一索引，二次下单创建索引失败保证幂等
     * 8.2：乐观锁思想，修改时判断订单为未支付状态才能修改成已支付状态
     * 8.3：唯一编号
     * <p>
     * 分布式事务：
     * seata:  XA模式
     * AT模式    @globalTransactional + @transactional,数据库建undoLog表
     * TCC模式   预留资源，事务控制表      @globalTransactional+@localTcc+@twoPhaseBusinessAction（定义提交和回滚方法）   上下文传参
     * RocketMQ: 事务消息：本地事务执行前发一条事务消息（消费者暂时收不到消息），消息监听器里面本地事务执行成功后，真正发送消息，消费者消费，执行失败就直接删除事务消息；
     * 这种方式需要消费方一定能执行成功，比如退款，付款就不行，付款可能余额不足，所以只适用特定场景
     * 事务补偿：消息监听器会定期查询本地事务状态，成功也会告知消费者
     */
    public void secKill(String secId) {
        LockSupport.park();
    }


    /**
     * 交替打印ab
     */
    public static void sout() {
        ReentrantLock lock = new ReentrantLock();
        Condition a = lock.newCondition();
        final boolean[] flag = {true};
        Thread threadA = new Thread(() -> {
            lock.lock();
            try {
                while (true) {
                    if (flag[0]) {
                        System.out.println("a");
                        flag[0] = false;
                        a.signal();
                    } else {
                        a.await();
                    }
                }
            } catch (Exception e) {
                System.out.println("报错");
            } finally {
                lock.unlock();
            }
        });

        Thread threadB = new Thread(() -> {
            lock.lock();
            try {
                while (true) {
                    if (!flag[0]) {
                        System.out.println("b");
                        flag[0] = true;
                        a.signal();
                    } else {
                        a.await();
                    }
                }
            } catch (Exception e) {
                System.out.println("报错");
            } finally {
                lock.unlock();
            }
        });
        threadA.start();
        threadB.start();

    }

    public static void main(String[] args) {
        sout();
    }
}
