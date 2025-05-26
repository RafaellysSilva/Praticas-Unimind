using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

[Table("Questao", Schema = "unimind")]
public class Questao
{
    [Key]
    [Column("idQuestao")]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)] 
    public int IdQuestao { get; set; }

    [Column("idProva")]
    public int IdProva { get; set; }

    [Column("idCategoria")]
    public int IdCategoria { get; set; }

    [Column("questao")]
    public string Pergunta { get; set; }

    [Column("resposta")]
    public string Resposta { get; set; }
}
