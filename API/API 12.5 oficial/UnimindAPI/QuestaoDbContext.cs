using Microsoft.EntityFrameworkCore;
public class QuestaoDbContext : DbContext
{
    public QuestaoDbContext(DbContextOptions<QuestaoDbContext> options) : base(options)
    {
    }
    public DbSet<Questao> Questoes { get; set; }
}