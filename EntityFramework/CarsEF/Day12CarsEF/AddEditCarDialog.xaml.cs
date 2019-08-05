using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace Day12CarsEF
{
    /// <summary>
    /// Interaction logic for AddEditCarDialog.xaml
    /// </summary>
    public partial class AddEditCarDialog : Window
    {
        Car selectedCar;

        public AddEditCarDialog(MainWindow owner, Car car = null)
        {
            Owner = owner;
            selectedCar = car;
            InitializeComponent();
            btSave.Content = selectedCar == null ? "Add car" : "Update car";
            if (selectedCar != null)
            {
                lblId.Content = selectedCar.Id;
                tbMakeModel.Text = selectedCar.MakeModel;
                sliderEngineSize.Value = selectedCar.EngineSizeL;
                comboFuelType.SelectedItem = selectedCar.FuelType;
            }
        }

        private void BtSave_Click(object sender, RoutedEventArgs e)
        {
            // add/update database records
            string makeModel = tbMakeModel.Text;
            // make sure we save the rounded value: 3.478263874623873 => 3.5
            double esRaw = sliderEngineSize.Value;
            double engineSize = double.Parse($"{esRaw:0.0}");
            // double engineSize = sliderEngineSize.Value;
            //
            FuelType fuelType = (FuelType)Enum.Parse(typeof(FuelType), comboFuelType.Text);

            if (selectedCar != null)
            {
                try { 
                selectedCar.MakeModel = makeModel;
                selectedCar.EngineSizeL = engineSize;
                selectedCar.FuelType = fuelType;
                Globals.ctx.SaveChanges();
                }
                catch (DataException ex)
                { // TODO: make message box nicer
                    MessageBox.Show("Database error:\n" + ex.Message);
                }
                catch (SystemException ex)
                { // TODO: make message box nicer
                    MessageBox.Show("Database error:\n" + ex.Message);
                }
            }
            else
            {
                try
                {   
                    Car car = new Car() { MakeModel = makeModel, EngineSizeL = engineSize, FuelType = fuelType };
                    Globals.ctx.Cars.Add(car);
                    Globals.ctx.SaveChanges();
                    DialogResult = true; // close dialog
                }
                catch (DataException ex)
                { // TODO: make message box nicer
                    MessageBox.Show("Database error:\n" + ex.Message);
                }
                catch (SystemException ex)
                { // TODO: make message box nicer
                    MessageBox.Show("Database error:\n" + ex.Message);
                }
            }
        }

        
    }
}
