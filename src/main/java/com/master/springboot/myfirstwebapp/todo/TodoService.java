package com.master.springboot.myfirstwebapp.todo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;

@Service
public class TodoService {

	private static List<Todo> todos = new ArrayList<Todo>();

	private static int todosCount = 0;

	static {
		todos.add(new Todo(++todosCount, "Satya", "Learn AWS 1", LocalDate.now().plusYears(1), false));
		todos.add(new Todo(++todosCount, "Satya", "Learn Devops 1", LocalDate.now().plusYears(2), false));
		todos.add(
				new Todo(++todosCount, "Satya", "Learn Full Stack Development 1", LocalDate.now().plusYears(3), false));
	}

	public List<Todo> findByUsername(String username) {
		Predicate<? super Todo> predicate = todo -> todo.getUserName().equalsIgnoreCase(username);
		return todos.stream().filter(predicate).toList();
	}

	public void addTodo(String username, String description, LocalDate targetDate, boolean done) {

		Todo todo = new Todo(++todosCount, username, description, targetDate, done);
		todos.add(todo);

	}

	public void deleteById(int id) {

		// todo.getId() == id
		// todo -> todo.getId() == id

		Predicate<? super Todo> predicate = todo -> todo.getId() == id;
		todos.removeIf(predicate);
	}

	public void updateTodo(Todo todo) {
		deleteById(todo.getId());
		todos.add(todo);
	}

	public Todo findById(int id) {
		Predicate<? super Todo> predicate = todo -> todo.getId() == id;
		Todo todo = todos.stream().filter(predicate).findFirst().get();
		return todo;
	}

}
