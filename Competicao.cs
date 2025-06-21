using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using static System.Runtime.InteropServices.JavaScript.JSType;

[Table("Competicao", Schema = "unimind")]
public class Competicao
{
    [Key]
    [Column("idCompeticao")]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)] 
    public int IdCompeticao { get; set; }

    [Column("data")]
    public DateOnly Data { get; set; }

    [Column("idUsuario1")]
    public int IdUsuario1 { get; set; }

    [Column("idUsuario2")]
    public int IdUsuario2 { get; set; }

    [Column("idNivel")]
    public int IdNivel { get; set; }
}
