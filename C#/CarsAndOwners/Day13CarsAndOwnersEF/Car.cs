using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Day13CarsAndOwnersEF
{
    public class Car
    { // table Cars
        public int Id { get; set; }

        [NotMapped]
        private string _makeModel;

        [StringLength(100)][Required]
        public string MakeModel
        {
            get
            {
                return _makeModel;
            }
            set
            {
                if (value.Length > 100)
                {
                    throw new ArgumentException("The name of make and model should nor exceed 100 characters");
                }
                else
                {
                    _makeModel = value;
                }
            }
        } // up to 100 characters
        public int YearOfProd { get; set; }

        public virtual Owner Owner { get; set; } // relation, may be null

        public override string ToString()
        {
            return $"{Id}: {MakeModel}, {YearOfProd}";
        }
    }
}
