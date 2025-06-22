// rafaellyssilva/praticas-unimind/Praticas-Unimind-API/Questao.cs
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

[Table("Questao", Schema = "unimind")]
public class Questao
{
    [Key]
    [Column("idQuestao")]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    public int IdQuestao { get; set; }

    [Column("ano")]
    public int Ano { get; set; }

    [Column("idCategoria")]
    public int IdCategoria { get; set; }

    [Column("fonte")]
    public string Fonte { get; set; }

    [Column("questao")]
    public string Pergunta { get; set; }

    [Column("resposta")]
    public string Resposta { get; set; }
}