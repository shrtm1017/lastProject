package kr.or.nko.todolist.dao;

import java.util.List;

import kr.or.nko.todolist.model.TodolistVo;

public interface ITodolistDao {
	
	public List<TodolistVo> selectTodo(int emp_sq);
	
	public int insertTodo(TodolistVo tdvo);
	
	public int updateTodo(TodolistVo tdvo);
	
	public int deleteTodo(TodolistVo tdvo);
	
	public int updateTodochk(TodolistVo tdvo);
}
