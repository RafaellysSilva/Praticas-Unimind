using Microsoft.EntityFrameworkCore;
using TWTodos.Data.EntityConfigs;
using TWTodos.Models;


namespace TWTodos.Data.Contexts;

/*
Uma instância de DbContext representa 
uma sessão com o banco de dados e pode 
ser usada para consultar e salvar instâncias 
de suas entidades. DbContext é uma combinação 
dos padrões Unidade de Trabalho e Repositório.
*/
public class TWTodoContext: DbContext
{

    // A table Todos do banco de dados terá as colunas do Model class Todo
    // propriedades Id,Name,Date,IsCompleted
    public DbSet<Todo> Todos => Set<Todo>();

    protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
    {
        //Configura o context para conectar o SQLite database.
        //recebe o parâmetro com o nome do banco de dados twtodos.sqlite3
        optionsBuilder.UseSqlite("Data Source=twtodos.sqlite3");
    }

    protected override void OnModelCreating(ModelBuilder builder){
        //Seta as configurações de tabela instanciando a classe TodoEntityConfig
        builder.ApplyConfiguration(new TodoEntityConfig());
    }



}