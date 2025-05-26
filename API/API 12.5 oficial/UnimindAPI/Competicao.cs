using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using static System.Runtime.InteropServices.JavaScript.JSType;

[Table("Competicao", Schema = "unimind")]
public class Competicao
{
    [Key]
    [Column("idCompeticao")]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)] 
    public int idCompeticao { get; set; }

    [Column("data")]
    public Date data { get; set; }

    [Column("idUsuario1")]
    public int idUsuario1 { get; set; }

    [Column("idUsuario2")]
    public int idUsuario2 { get; set; }

    [Column("idNivel")]
    public int idNivel { get; set; }
}
