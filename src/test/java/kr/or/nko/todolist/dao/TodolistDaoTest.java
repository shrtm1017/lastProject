package kr.or.nko.todolist.dao;

import static org.junit.Assert.*;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.nko.testconfig.LogicTestConfig;
import kr.or.nko.todolist.dao.ITodolistDao;
import kr.or.nko.todolist.model.TodolistVo;

public class TodolistDaoTest extends LogicTestConfig{
	
	private Logger logger = LoggerFactory.getLogger(TodolistDaoTest.class);

	@Resource(name="todolistDao")
	private ITodolistDao todolistDao; 
	
	@Test
	public void testSelectTodolist() {
		/***Given***/
		int emp_sq = 111111;
		
		/***When***/
		List<TodolistVo> list = todolistDao.selectTodo(emp_sq);
		logger.debug("list.size() : {}", list.size());
		
		/***Then***/

	}
	
	@Test
	public void testInsertTodo(){
		/***Given***/
		
		/***When***/
		TodolistVo tdvo = new TodolistVo();
		tdvo.setTdl_emp_sq(111111);
		tdvo.setTdl_con("test4");
		tdvo.setTdl_comp("n");
		
		int cnt = todolistDao.insertTodo(tdvo);
		/***Then***/
		assertEquals(cnt, 1);
	}

}
