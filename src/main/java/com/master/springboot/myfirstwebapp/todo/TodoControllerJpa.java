package com.master.springboot.myfirstwebapp.todo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.validation.Valid;

@SessionAttributes("name")
@Controller
public class TodoControllerJpa {
  	
	private TodoRepository todoRepository;
	
	public TodoControllerJpa(TodoRepository todoRepository) {
		super();
		this.todoRepository = todoRepository;
	}
	
	@RequestMapping("list-todo")
	public String listAllTodos(ModelMap model) {
		List<Todo> todos = todoRepository.findByUserName(getLoggedInUserName());
		model.put("todos", todos);
		
		return "listTodo";
	}

	private String getLoggedInUserName() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getName();
	}
	
	// this will handle all get or post requests
//	@RequestMapping("add-todo")
//	public String showTodoPage() {
//		return "todo";
//	}
	
	@RequestMapping(value="add-todo", method=RequestMethod.GET)
	public String showTodoPage(ModelMap model) {
		Todo todo = new Todo(0, getLoggedInUserName(), "", LocalDate.now().plusYears(1), false);
		model.put("todo", todo);
		return "todo";
	}
	
	@RequestMapping(value="add-todo", method=RequestMethod.POST)
	public String addNewTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
		
		if(result.hasErrors()) {
			return "todo";
		}
		todo.setUserName(getLoggedInUserName());
		todoRepository.save(todo);
		return "redirect:list-todo";
	}
	
	@RequestMapping("delete-todo")
	public String deleteTodo(@RequestParam int id) {
		
		todoRepository.deleteById(id);
		return "redirect:list-todo";
	}
	
	@RequestMapping(value = "update-todo", method=RequestMethod.GET)
	public String showUpdateTodo(@RequestParam int id, ModelMap model) {
		Todo todo = todoRepository.findById(id).get();
		model.put("todo", todo);
		return "todo";
	}
	
	@RequestMapping(value="update-todo", method=RequestMethod.POST)
	public String updateTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
		
		if(result.hasErrors()) {
			return "todo";
		}
		
		String userName = getLoggedInUserName();
		todo.setUserName(userName);
		todoRepository.save(todo);
		return "redirect:list-todo";
	}
	
	
}
