using Microsoft.EntityFrameworkCore;
public class ProvaDbContext : DbContext
{
    public ProvaDbContext(DbContextOptions<ProvaDbContext> options) : base(options)
    {
    }
    public DbSet<Prova> Provas { get; set; }
}