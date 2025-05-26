using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

[Table("Prova", Schema = "unimind")]
public class Prova
{
    [Key]
    [Column("idProva")]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)] 
    public int IdProva { get; set; }

    [Column("idFonte")]
    public int IdFonte { get; set; }

    [Column("ano")]
    public int Ano { get; set; }

    [Column("qntdQuestoes")]
    public int QntdQuestoes { get; set; }

    [Column("fase")]
    public string Fase { get; set; }
}
