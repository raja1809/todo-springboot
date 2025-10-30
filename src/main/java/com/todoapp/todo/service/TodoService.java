package com.todoapp.todo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.todoapp.todo.model.Todo;
import com.todoapp.todo.repository.TodoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoService {
    
    private final TodoRepository todoRepository;
    
    // Get all todos
    public List<Todo> getAllTodos() {
        return todoRepository.findAllByOrderByCreatedAtDesc();
    }
    
    // Get a single todo by ID
    public Optional<Todo> getTodoById(Long id) {
        return todoRepository.findById(id);
    }
    
    // Create a new todo
    public Todo createTodo(Todo todo) {
        todo.setCompleted(false); // Ensure new todos start as incomplete
        return todoRepository.save(todo);
    }
    
    // Update an existing todo
    public Optional<Todo> updateTodo(Long id, Todo todoDetails) {
        return todoRepository.findById(id)
            .map(existingTodo -> {
                existingTodo.setTitle(todoDetails.getTitle());
                existingTodo.setDescription(todoDetails.getDescription());
                existingTodo.setCompleted(todoDetails.getCompleted());
                return todoRepository.save(existingTodo);
            });
    }
    
    // Toggle completion status
    public Optional<Todo> toggleTodoComplete(Long id) {
        return todoRepository.findById(id)
            .map(todo -> {
                todo.setCompleted(!todo.getCompleted());
                return todoRepository.save(todo);
            });
    }
    
    // Delete a todo
    public boolean deleteTodo(Long id) {
        if (todoRepository.existsById(id)) {
            todoRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    // Get todos by completion status
    public List<Todo> getTodosByStatus(Boolean completed) {
        return todoRepository.findByCompleted(completed);
    }
    
    // Search todos by title
    public List<Todo> searchTodos(String keyword) {
        return todoRepository.findByTitleContainingIgnoreCase(keyword);
    }
}