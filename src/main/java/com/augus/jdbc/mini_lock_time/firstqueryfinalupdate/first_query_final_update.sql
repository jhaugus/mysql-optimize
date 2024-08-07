# 在事务中先执行查询，再执行更新操作
# 因为查询没有加锁，所以其他事务可以同时查询
# 但是更新操作会加锁，所以其他事务不能同时更新
# 这样锁的时间就会更低，并发度会更高

begin;
select * from user where id = 1;
update user set name = 'new name' where id = 1;
commit;

# 这个只是示例而已，不能运行