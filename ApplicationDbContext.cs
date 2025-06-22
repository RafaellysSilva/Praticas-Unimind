using Microsoft.EntityFrameworkCore;

public class ApplicationDbContext : DbContext
{
    public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options) : base(options)
    {
    }

    public DbSet<Usuario> Usuarios { get; set; }
    public DbSet<Questao> Questoes { get; set; }
    public DbSet<ListaPersonalizada> ListaPersonalizadas { get; set; }
    public DbSet<Flashcard> Flashcards { get; set; }
    public DbSet<Competicao> Competicoes { get; set; }
}