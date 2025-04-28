using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using TWTodos.Models;
namespace TWTodos.Data.EntityConfigs;

public class TodoEntityConfig: IEntityTypeConfiguration<Todo>{

    public void Configure(EntityTypeBuilder<Todo> builder){

        //Cria a Tabela
        builder.ToTable("Todos");

        //Cria a chave primária
        builder.HasKey(t => t.Id);

        //Cria a coluna name
        builder.Property(t => t.Name)
            .HasMaxLength(100)
            .IsRequired();

        //Cria a coluna Date
        builder.Property(t => t.Date)
            .IsRequired();

        //coluna IsCompleted
        builder.Property(t => t.IsCompleted)
            .IsRequired();
    }
}        

