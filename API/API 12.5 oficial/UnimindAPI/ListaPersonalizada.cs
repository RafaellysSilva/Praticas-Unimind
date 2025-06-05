using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

[Table("ListaPersonalizada", Schema = "unimind")]
public class ListaPersonalizada
{
    [Key]
    [Column("idLista")]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)] 
    public int IdLista { get; set; }

    [Column("idUsuario")]
    public int IdUsuario { get; set; }

    [Column("idCategoria")]
    public int IdCategoria { get; set; }

    [Column("idFonte")]
    public int? IdFonte { get; set; }

    [Column("ano")]
    public int? Ano { get; set; }
}
