package kr.or.nko.todolist.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.nko.todolist.dao.ITodolistDao;
import kr.or.nko.todolist.model.TodolistVo;

@Service("todolistService")
public class TodolistServiceImpl implements ITodolistService{
	
	@Resource(name="todolistDao")
	private ITodolistDao todolistDao;
	
	@Override
	public List<TodolistVo> selectTodo(int emp_sq) {
		return todolistDao.selectTodo(emp_sq);
	}

	@Override
	public int insertTodo(TodolistVo tdvo) {
		return todolistDao.insertTodo(tdvo);
	}

	@Override
	public int updateTodo(TodolistVo tdvo) {
		return todolistDao.updateTodo(tdvo);
	}

	@Override
	public int deleteTodo(TodolistVo tdvo) {
		return todolistDao.deleteTodo(tdvo);
	}

	@Override
	public int updateTodochk(TodolistVo tdvo) {
		return todolistDao.updateTodochk(tdvo);
	}

}
