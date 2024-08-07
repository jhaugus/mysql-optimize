# 通过分批删除的方法，减少锁的持有时间
# 一次性删除太多记录，会导致锁的持有时间过长，影响其他操作

DELETE FROM `t_user_batch` WHERE add_time < 1723043695 LIMIT 30000;

DELETE FROM `t_user_batch`
WHERE uid IN (
    SELECT uid
    FROM (
             SELECT uid
             FROM `t_user_batch`
             WHERE add_time < 1723043695
             LIMIT 10000
         ) AS temp_table
);

