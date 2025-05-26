using Microsoft.EntityFrameworkCore;
public class CompeticaoDbContext : DbContext
{
    public CompeticaoDbContext(DbContextOptions<CompeticaoDbContext> options) : base(options)
    {
    }
    public DbSet<Competicao> Competicoes { get; set; }
}