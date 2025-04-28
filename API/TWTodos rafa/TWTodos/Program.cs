using TWTodos.Data.Contexts;
using TWTodos.Models;


var builder = WebApplication.CreateBuilder(args);

//configura a WebApplication Todo para usar a classe db context TWTTodoConrext
builder.Services.AddDbContext<TWTodoContext>();

var app = builder.Build();

//Inserir registro
app.MapPost("/api/todos", (Todo todo, TWTodoContext context) =>
{
    context.Todos.Add(todo);
    context.SaveChanges();
    return Results.Created($"/api/todos/{todo.Id}", todo);
});


//Listar registros
app.MapGet("/api/todos", (TWTodoContext context) => Results.Ok(context.Todos));

//Listar um registro
app.MapGet("/api/todos/{id}", (int id,TWTodoContext context) => 
{
    var item  = context.Todos.Find(id);
    if(item is null){
        return Results.NotFound();
    }
    return Results.Ok(item);

});   

//Alterar registro
app.MapPut("/api/todos/{id}", (int id,Todo todo,TWTodoContext context) =>
{
    var item = context.Todos.Find(id);
    if (item is null)
    {
        return Results.NotFound();
    }
    item.Name = todo.Name;
    item.Date = todo.Date;
    item.IsCompleted = todo.IsCompleted;
    context.Todos.Update(todo);
    context.SaveChanges();
    return Results.Ok(item);
});

//Excluir um registro
app.MapDelete("/api/todos/{id}", (int id,TWTodoContext context) =>
{
    var item = context.Todos.Find(id);
    if (item is null)
    {
        return Results.NotFound();
    }

    context.Remove(item);
    context.SaveChanges();
    return Results.NoContent();
});


Console.WriteLine();
app.Run();