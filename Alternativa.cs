using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text.Json.Serialization;

[Table("Alternativa", Schema = "unimind")]
public class Alternativa
{
    [Key]
    [Column("idAlternativa")]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    public int IdAlternativa { get; set; }

    [Column("texto")]
    public string Texto { get; set; }

    [Column("correta")]
    public bool Correta { get; set; }

    [Column("idQuestao")]
    public int IdQuestao { get; set; }

    [JsonIgnore]
    [ForeignKey("IdQuestao")]
    public virtual Questao? Questao { get; set; }
}