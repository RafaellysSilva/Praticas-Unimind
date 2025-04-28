using Microsoft.EntityFrameworkCore;
using TWTodos.Models;

namespace TWTodos.Data.Contexts;

public class TWTodoContext : DbContext
{
    public TWTodoContext(DbContextOptions<TWTodoContext> options)
        : base(options)
    {
    }

    public DbSet<Todo> Todos { get; set; }
}