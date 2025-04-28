using TWTodos.Data.Contexts;
using Microsoft.EntityFrameworkCore;

var builder = WebApplication.CreateBuilder(args);

//configura a WebApplication Todo para usar a classe db context TWTTodoConrext
builder.Services.AddDbContext<TWTodoContext>(static options => 
    options.UseSqlServer("Server=regulus;Database=BD24140;User Id=BD24140;Password=BD24140;TrustServerCertificate=True;"));

var app = builder.Build();

//Listar registros
app.MapGet("/api", (TWTodoContext context) => 
    Results.Ok(context.Todos));

app.MapGet("/", () => "Hello World!");

app.Run();
