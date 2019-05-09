package kr.or.nko.todolist.dao;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.nko.todolist.model.TodolistVo;

@Repository("todolistDao")
public class TodolistDaoImpl implements ITodolistDao {

	@Resource(name="sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;

	@Override
	public List<TodolistVo> selectTodo(int emp_sq) {
		return sqlSessionTemplate.selectList("todolist.selectTodo", emp_sq);
	}

	@Override
	public int insertTodo(TodolistVo tdvo) {
		return sqlSessionTemplate.insert("todolist.insertTodo",tdvo);
	}

	@Override
	public int updateTodo(TodolistVo tdvo) {
		return sqlSessionTemplate.update("todolist.updateTodo",tdvo);
	}

	@Override
	public int deleteTodo(TodolistVo tdvo) {
		return sqlSessionTemplate.delete("todolist.deleteTodo",tdvo);
	}

	@Override
	public int updateTodochk(TodolistVo tdvo) {
		return sqlSessionTemplate.update("todolist.updateTodochk",tdvo);
	}
}