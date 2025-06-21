using Microsoft.EntityFrameworkCore;
public class FlashcardDbContext : DbContext
{
    public FlashcardDbContext(DbContextOptions<FlashcardDbContext> options) : base(options)
    {
    }
    public DbSet<Flashcard> Flashcards { get; set; }
}