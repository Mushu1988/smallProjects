using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Day13TravelsEF
{
    public class TravelDbContext: DbContext
    {
        public DbSet<Train> Trains { get; set; }
        public DbSet<Passenger> Passengers { get; set; }
    }
}
