using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

[Table("Flashcard", Schema = "unimind")]
public class Flashcard
{
    [Key]
    [Column("idFlashcard")]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    public int IdFlashcard { get; set; }

    [Column("idUsuario")]
    public int IdUsuario { get; set; }

    [Column("perguntaUsuario")]
    public string PerguntaUsuario { get; set; }
    
    [Column("respostaUsuario")]
    public string RespostaUsuario { get; set; }
}
