using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Day12CarsEF 
{
    public class CarDBContext : DbContext
    {
        public virtual DbSet<Car> Cars { get; set; }
    }
}
