# 《 实验四：SQL练习》

**学院：**省级示范性软件学院

**题目：**《 实验四：SQL练习》

**姓名：**邹子昂

**学号：**2100240124

**班级：**软工2202

**日期：**2024-10-10

**实验环境：**MySQL8



### 一、员工信息题

##### 1. 查询所有员工的姓名、邮箱和工作岗位。

```sql
select concat(last_name, " ", first_name) as emp_name, email, job_title
from employees; 
```

##### 2. 查询所有部门的名称和位置。

```sql
select dept_name, location
from departments;
```

##### 3. 查询工资超过70000的员工姓名和工资。

```sql
select concat(last_name, " ", first_name) as emp_name, salary
from employees
where salary > 70000;
```

##### 4. 查询IT部门的所有员工。

```sql
select emp_id, concat(last_name, " ", first_name) as emp_name
from employees e, departments d
where e.dept_id = d.dept_id and dept_name = "IT";
```

##### 5. 查询入职日期在2020年之后的员工信息。

```sql
select emp_id, concat(last_name, " ", first_name) as emp_name, email, phone_number, hire_date, job_title, salary, dept_id
from employees
where hire_date > "2020-12-31";
```

##### 6. 计算每个部门的平均工资。

```sql
select dept_name, avg(salary)
from departments d, employees e
where e.dept_id = d.dept_id
group by d.dept_id;
```

##### 7. 查询工资最高的前3名员工信息。

```sql
with rank_employees as (
	select emp_id, concat(last_name, " ", first_name) as emp_name, email, phone_number, hire_date, job_title, salary, e.dept_id, rank() over( order by salary DESC) as salary_rank
    from employees e, departments d
    where e.dept_id = d.dept_id
)
select emp_id, emp_name, email, phone_number, hire_date, job_title, salary, dept_id, salary_rank
from rank_employees
where salary_rank <= 3;
```

##### 8. 查询每个部门员工数量。

```sql
select dept_name, count(*) emp_num
from departments d,  employees e
where d.dept_id = e.dept_id
group by d.dept_id;
```

##### 9. 查询没有分配部门的员工。

```sql
select emp_id, concat(last_name, " ", first_name) as emp_name
from employees
where dept_id not in (
	select dept_id
    from departments
);
```

##### 10. 查询参与项目数量最多的员工。

```sql
with total_ep as (
	select ep.emp_id, concat(last_name, " ", first_name) as emp_name, count(ep.emp_id) as total_projects
    from employee_projects ep,  employees e
	where ep.emp_id = e.emp_id
	group by ep.emp_id
),
rank_projects as (
	select emp_id, emp_name, total_projects, rank() over(order by total_projects DESC) as pro_rank
    from total_ep
)
select emp_id, emp_name, total_projects, pro_rank
from rank_projects
where pro_rank <= 1;
```

##### 11. 计算所有员工的工资总和。
```sql
select sum(salary) sum_salary
from employees;
```

##### 12. 查询姓"Smith"的员工信息。
```sql
select emp_id, concat(last_name, " ", first_name) as emp_name, email, phone_number, hire_date, job_title, salary, dept_id
from employees
where last_name like "Smith";
```

##### 13. 查询即将在半年内到期的项目。
```sql
select project_name, start_date, end_date
from projects
where end_date between start_date and date_add(start_date, interval 6 month);
```

##### 14. 查询至少参与了两个项目的员工。
```sql
with ep_total as (
	select e.emp_id, concat(last_name, " ", first_name) as emp_name, email, phone_number, hire_date, job_title, salary, dept_id, count(*) as total_projects
    from employees e, employee_projects ep
    where e.emp_id = ep.emp_id
    group by e.emp_id
)
select emp_id, emp_name, email, phone_number, hire_date, job_title, salary, dept_id, total_projects
from ep_total
where total_projects >= 2;
```

##### 15. 查询没有参与任何项目的员工。
```sql
select emp_id, concat(last_name, " ", first_name) as emp_name, email, phone_number, hire_date, job_title, salary, dept_id
from employees
where emp_id not in (
	select distinct emp_id
    from employee_projects
);
```

##### 16. 计算每个项目参与的员工数量。
```sql
select ep.project_id, project_name, count(emp_id) as emp_num
from employee_projects ep, projects p
where ep.project_id = p.project_id
group by ep.project_id;
```

##### 17. 查询工资第二高的员工信息。
```sql
with rank_emp as (
	select emp_id, concat(last_name, " ", first_name) as emp_name, email, phone_number, hire_date, job_title, salary, dept_id, 
		   rank() over( order by salary DESC ) as rank_salary
	from employees
)
select emp_id, emp_name, email, phone_number, hire_date, job_title, salary, dept_id, rank_salary
from rank_emp
where rank_salary = 2;
```

##### 18. 查询每个部门工资最高的员工。
```sql
with rank_emp as (
	select emp_id, concat(last_name, " ", first_name) as emp_name, email, phone_number, hire_date, job_title, salary, dept_id, 
		   rank() over(partition by dept_id order by salary DESC) as rank_salary
	from employees
)
select emp_id, emp_name, email, phone_number, hire_date, job_title, salary, dept_id, rank_salary
from rank_emp
where rank_salary = 1;
```

##### 19. 计算每个部门的工资总和,并按照工资总和降序排列。
```sql
with dept_salary as (
	select d.dept_id, dept_name, sum(salary) as total_salary
    from departments d, employees e
    where d.dept_id = e.dept_id
    group by d.dept_id
)
select dept_id, dept_name, total_salary
from dept_salary
order by total_salary DESC;
```

##### 20. 查询员工姓名、部门名称和工资。
```sql
select concat(last_name, " ", first_name) as emp_name, dept_name, salary
from employees e, departments d
where e.dept_id = d.dept_id;
```

##### 23. 查询平均工资最高的部门。
```sql
with dept_salary as (
	select d.dept_id, dept_name, avg(salary) as avg_salary
    from departments d, employees e
    where d.dept_id = e.dept_id
    group by d.dept_id
),
ranked_salary as (
	select dept_id, dept_name, avg_salary, 
		   rank() over(order by avg_salary DESC) as rank_s
	from dept_salary
)
select dept_id, dept_name, avg_salary
from ranked_salary
where rank_s = 1;
```

##### 24. 查询工资高于其所在部门平均工资的员工。
```sql
with dept_salary as (
	select d.dept_id, dept_name, avg(salary) as avg_salary
    from departments d, employees e
    where d.dept_id = e.dept_id
    group by d.dept_id
)
select emp_id, concat(last_name, " ", first_name) as emp_name, email, phone_number, hire_date, job_title, salary, e.dept_id, avg_salary
from employees e, dept_salary ds
where salary > avg_salary and e.dept_id = ds.dept_id;
```

##### 25. 查询每个部门工资前两名的员工。

```sql
with rank_emp as (
	select emp_id, concat(last_name, " ", first_name) as emp_name, email, phone_number, hire_date, job_title, salary, dept_id, 
		   rank() over(partition by dept_id order by salary DESC) as rank_salary
	from employees
)
select emp_id, emp_name, email, phone_number, hire_date, job_title, salary, dept_id, rank_salary
from rank_emp
where rank_salary <= 2;
```

##### 26. 查询跨部门的项目(参与员工来自不同部门)。

```sql
with projects_emp as (
	select p.project_id, project_name, count(dept_id) as dept_num
    from projects p, employee_projects ep, employees e
    where p.project_id = ep.project_id and e.emp_id = ep.emp_id
    group by ep.project_id
)
select project_id, project_name, dept_num
from projects_emp
where dept_num > 1;
```

##### 27. 查询每个员工的工作年限,并按工作年限降序排序。

```sql
select concat(last_name, " ", first_name) as emp_name, hire_date, extract(year from current_date) - extract(year from hire_date) as years
from employees
order by years DESC;
```



### 二、学生选课题

##### 1. 查询所有学生的信息。

```sql
select student_id, name, gender, birth_date, my_class
from student;
```

##### 2. 查询所有课程的信息。
```sql
select course_id, course_name, teacher_id, credits
from course;
```

##### 3. 查询所有学生的姓名、学号和班级。
```sql
select name, student_id, my_class
from student;
```

##### 4. 查询所有教师的姓名和职称。
```sql
select name, title
from teacher;
```

##### 5. 查询不同课程的平均分数。
```sql
select course_name, avg(score) as avg_score
from course c, score s
where c.course_id = s.course_id
group by c.course_id;
```

##### 6. 查询每个学生的平均分数。
```sql
select st.student_id, name, avg(score) as avg_score
from student st, score sc
where st.student_id = sc.student_id
group by sc.student_id;
```

##### 7. 查询分数大于85分的学生学号和课程号。
```sql
select s.student_id, sc.course_id
from student s, course c, score sc
where s.student_id = sc.student_id and c.course_id = sc.course_id and score > 85;
```

##### 8. 查询每门课程的选课人数。
```sql
select c.course_id, c.course_name, count(*) as student_num
from score s, course c
where s.course_id = c.course_id
group by s.course_id;
```

##### 9. 查询选修了"高等数学"课程的学生姓名和分数。
```sql
select name, score
from student s, score sc, course c
where s.student_id = sc.student_id and c.course_id = sc.course_id and course_name = "高等数学";
```

##### 10. 查询没有选修"大学物理"课程的学生姓名。
```sql
select name
from student s
where student_id not in (
	select student_id
	from score sc, course c
	where s.student_id = sc.student_id and c.course_id = sc.course_id and course_name = "大学物理"
);
```

##### 11. 查询C001比C002课程成绩高的学生信息及课程分数。
```sql
select s.student_id, name, sc1.score as C001_score, sc2.score as C002_score
from student s, score sc1, score sc2
where s.student_id = sc1.student_id and s.student_id = sc2.student_id and
	  sc1.course_id = "C001" and sc2.course_id = "C002" and sc1.score > sc2.score;
```

##### 13. 查询选择C002课程但没选择C004课程的成绩情况(不存在时显示为 null )。
```sql
select distinct s.student_id, name, sc1.score as C002_score
from student s, score sc1, score sc2
where s.student_id = sc1.student_id and s.student_id = sc2.student_id and
	  sc1.course_id = "C002" and sc2.student_id not in (
		select sc2.student_id
		from course c
		where c.course_id = sc2.course_id and c.course_id = "C004"
);
```

##### 14. 查询平均分数最高的学生姓名和平均分数。
```sql
with rank_student as (
	select st.student_id, name, avg(score) as avg_score, rank() over(order by avg(score) DESC) as ranking
	from student st, score sc
	where st.student_id = sc.student_id
	group by sc.student_id
)
select name, avg_score
from rank_student
where ranking = 1;
```

##### 15. 查询总分最高的前三名学生的姓名和总分。
```sql
with rank_student as (
	select st.student_id, name, sum(score) as sum_score, rank() over(order by sum(score) DESC) as ranking
	from student st, score sc
	where st.student_id = sc.student_id
	group by sc.student_id
)
select name, sum_score
from rank_student
where ranking <= 3;
```

##### 17. 查询男生和女生的人数。
```sql
select gender, count(gender) as num
from student
group by gender;
```

##### 18. 查询年龄最大的学生姓名。
```sql
select name
from student
where birth_date = (
	select min(birth_date)
    from student
);
```

##### 19. 查询年龄最小的教师姓名。
```sql
select name
from teacher
where birth_date = (
	select max(birth_date)
    from teacher
);
```

##### 20. 查询学过「张教授」授课的同学的信息。
```sql
select distinct s.student_id, s.name
from student s, score sc, course c, teacher t
where s.student_id = sc.student_id and c.course_id = sc.course_id and 
	  c.teacher_id = t.teacher_id and t.name = "张教授";
```

##### 21. 查询查询至少有一门课与学号为"2021001"的同学所学相同的同学的信息 。
```sql
select distinct s.student_id, name
from student s, score sc
where s.student_id = sc.student_id and s.student_id != "2021001" and 
	  sc.course_id in (
			select course_id
			from score
			where student_id = "2021001"
            );
```

##### 22. 查询每门课程的平均分数，并按平均分数降序排列。
```sql
select course_name, avg(score) as avg_score
from course c, score s
where c.course_id = s.course_id
group by c.course_id
order by avg(score) DESC;
```

##### 23. 查询学号为"2021001"的学生所有课程的分数。
```sql
select st.student_id, name, score
from student st, score sc
where st.student_id = sc.student_id and st.student_id = "2021001";
```

##### 24. 查询所有学生的姓名、选修的课程名称和分数。
```sql
select name, course_name, score
from student s, score sc, course c
where s.student_id = sc.student_id and c.course_id = sc.course_id;
```

##### 25. 查询每个教师所教授课程的平均分数。
```sql
select name, course_name, avg(score) as avg_score
from course c, score s, teacher t
where c.course_id = s.course_id and c.teacher_id = t.teacher_id
group by c.course_id;
```

##### 26. 查询分数在80到90之间的学生姓名和课程名称。
```sql
select name, course_name, score
from course c, score sc, student s
where c.course_id = sc.course_id and sc.student_id = s.student_id and score between 80 and 90;
```

##### 27. 查询每个班级的平均分数。
```sql
select my_class, avg(score) as avg_score
from course c, score sc, student s
where c.course_id = sc.course_id and sc.student_id = s.student_id
group by s.my_class;
```

##### 28. 查询没学过"王讲师"老师讲授的任一门课程的学生姓名。
```sql
select distinct s.name
from student s, score sc, course c, teacher t
where s.student_id = sc.student_id and c.course_id = sc.course_id and 
	  c.teacher_id = t.teacher_id and sc.course_id not in(
		select course_id
        from course c, teacher t
        where c.teacher_id = t.teacher_id and t.name = "王讲师"
      );
```

##### 29. 查询两门及其以上小于85分的同学的学号，姓名及其平均成绩 。
```sql
select st.student_id, name, avg(score) as avg_score
from student st, score sc
where st.student_id = sc.student_id and score < 85
group by sc.student_id
having count(*) >= 2;
```

##### 30. 查询所有学生的总分并按降序排列。
```sql
select name, sum(score) as sum_score
from student st, score sc
where st.student_id = sc.student_id
group by st.student_id
order by sum(score) DESC;
```

##### 31. 查询平均分数超过85分的课程名称。
```sql
select course_name, avg(score) as avg_score
from course c, score s
where c.course_id = s.course_id
group by c.course_id
having avg(score) > 85;
```

##### 32. 查询每个学生的平均成绩排名。
```sql
with rank_student as (
	select st.student_id, name, avg(score) as avg_score, rank() over(order by avg(score) DESC) as ranking
	from student st, score sc
	where st.student_id = sc.student_id
	group by sc.student_id
)
select student_id, name, avg_score, ranking
from rank_student;
```

##### 33. 查询每门课程分数最高的学生姓名和分数。
```sql
select sc.course_id, course_name, name, score
from student s, score sc, course c
where s.student_id = sc.student_id and c.course_id = sc.course_id and
	  score = (
		select max(score)
		from score
		where course_id = c.course_id
	  );
```

##### 34. 查询选修了"高等数学"和"大学物理"的学生姓名。
```sql
select distinct name
from student s
where s.student_id in (
    select sc1.student_id
    from score sc1, course c1
    where sc1.course_id = c1.course_id and c1.course_name = '高等数学'
)
and s.student_id in (
    select sc2.student_id
    from score sc2, course c2
    where sc2.course_id = c2.course_id and c2.course_name = '大学物理'
);
```

##### 35. 按平均成绩从高到低显示所有学生的所有课程的成绩以及平均成绩（没有选课则为空）。
```sql
with course_score as (
select s.student_id, course_name, score
from student s, score sc, course c
where s.student_id = sc.student_id and c.course_id = sc.course_id
)
select st.student_id, name, avg(sc.score) as avg_score, course_name, cs.score
from student st
left join score sc on st.student_id = sc.student_id
left join course_score cs on cs.student_id = st.student_id
group by st.student_id, st.name, cs.course_name, cs.score
order by avg_score DESC;
```

##### 36. 查询分数最高和最低的学生姓名及其分数。
```sql
with avg_scores as (
  select student_id, avg(score) as avg_score
  from score
  group by student_id
),
max_min_scores as (
  select student_id, avg_score
  from avg_scores
  where avg_score = (select max(avg_score) from avg_scores)
     or avg_score = (select min(avg_score) from avg_scores)
)
select name, avg_score
from student s, max_min_scores mms
where s.student_id = mms.student_id;
```

##### 37. 查询每个班级的最高分和最低分。
```sql
select my_class, max(score) as max_score, min(score) as min_score
from student s, score sc
where s.student_id = sc.student_id
group by my_class;
```

##### 38. 查询每门课程的优秀率（优秀为90分）。
```sql
select c.course_id, c.course_name, 
       round(sum(case when sc.score >= 90 then 1 else 0 end) * 100.0 / count(*), 2) as excellent_rate
from course c, score sc
where c.course_id = sc.course_id
group by c.course_id, c.course_name;
```

##### 39. 查询平均分数超过班级平均分数的学生。
```sql
with class_avg as (
  select my_class, avg(score) as avg_score
  from student s, score sc
  where s.student_id = sc.student_id
  group by my_class
),
student_avg as (
  select s.student_id, s.name, s.my_class, avg(sc.score) as avg_score
  from student s, score sc
  where s.student_id = sc.student_id
  group by s.student_id, s.name, s.my_class
)
select sa.student_id, sa.name
from student_avg sa, class_avg ca
where sa.my_class = ca.my_class and sa.avg_score > ca.avg_score;
```

##### 40. 查询每个学生的分数及其与课程平均分的差值。

```sql
with course_avg as (
  select course_id, avg(score) as avg_score
  from score
  group by course_id
)
select s.student_id, s.name, c.course_name, sc.score, (sc.score - ca.avg_score) as score_diff
from student s, score sc, course c, course_avg ca
where s.student_id = sc.student_id and sc.course_id = c.course_id and sc.course_id = ca.course_id;
```

##### 41. 查询至少有一门课程分数低于80分的学生姓名。

```sql
select distinct s.name
from student s, score sc
where s.student_id = sc.student_id and sc.score < 80;
```

##### 42. 查询所有课程分数都高于85分的学生姓名。

```sql
select s.name
from student s
where not exists (
  select *
  from score sc
  where sc.student_id = s.student_id and sc.score <= 85
);
