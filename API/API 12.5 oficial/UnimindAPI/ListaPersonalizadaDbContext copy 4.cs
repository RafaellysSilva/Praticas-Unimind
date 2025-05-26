using Microsoft.EntityFrameworkCore;
public class ListaPersonalizadaDbContext : DbContext
{
    public ListaPersonalizadaDbContext(DbContextOptions<ListaPersonalizadaDbContext> options) : base(options)
    {
    }
    public DbSet<ListaPersonalizada> ListaPersonalizadas { get; set; }
}