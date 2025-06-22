// rafaellyssilva/praticas-unimind/Praticas-Unimind-API/ListaPersonalizada.cs
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

    [Column("fonte")]
    public string? Fonte { get; set; }

    [Column("ano")]
    public int? Ano { get; set; }
}