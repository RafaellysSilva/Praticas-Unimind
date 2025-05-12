using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

[Table("Usuario", Schema = "unimind")]
public class Usuario
{
    [Key]
    [Column("idUsuario")]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)] 
    public int IdUsuario { get; set; }

    [Column("nome")]
    public string Nome { get; set; }

    [Column("email")]
    public string Email { get; set; }

    [Column("senha")]
    public string Senha { get; set; }

    [Column("idNivel")]
    public int IdNivel { get; set; }

    [Column("acertosQuestoes")]
    public int AcertosQuestoes { get; set; }

    [Column("errosQuestoes")]
    public int ErrosQuestoes { get; set; }

    [Column("tempoEstudo")]
    public int TempoEstudo { get; set; }

    [Column("competicoesRealizadas")]
    public int CompeticoesRealizadas { get; set; }
}
