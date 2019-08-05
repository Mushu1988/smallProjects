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

namespace Day13TravelsEF
{
    /// <summary>
    /// Interaction logic for ManagePassengersDialog.xaml
    /// </summary>
    public partial class ManagePassengersDialog : Window
    {
        public ManagePassengersDialog(MainWindow owner)
        {
            try
            {
                Owner = owner;
                InitializeComponent();
                Globals.ctx = new TravelDbContext();
                lvPassengers.ItemsSource = (from p in Globals.ctx.Passengers select p).ToList<Passenger>();

                var trainCollection = (from t in Globals.ctx.Trains select t).ToList<Train>();
                cmbTrain.Items.Add("");
                foreach (Train t in trainCollection)
                {
                    cmbTrain.Items.Add(t.Number.ToString());
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("Fatal error: " + ex.Message, Globals.AppName, MessageBoxButton.OK, MessageBoxImage.Error);
                Close();
            }
        }

        private void AddUpdatePassenger_ButtonClick(object sender, RoutedEventArgs e)
        {
            Button button = sender as Button;
            Boolean isUpdating = (button.Name == "btUpdatePassenger");

            string name = tbName.Text;

            Gender gender = Gender.NA;

            if (rbFemale.IsChecked == true)
            {
                gender = Gender.Female;
            }
            else if (rbMale.IsChecked == true)
            {
                gender = Gender.Male;
            }
            else if(rbNA.IsChecked==true)
            {
                gender = Gender.NA;
            }
            else
            {
                MessageBox.Show("The gender field cannot be empty", Globals.AppName, MessageBoxButton.OK, MessageBoxImage.Exclamation);
            }
            Train train = null;
            if (cmbTrain.SelectedValue != null)
            {
                string trainNum = cmbTrain.SelectedValue.ToString();
                if (!int.TryParse(trainNum, out int trainNumber))
                {
                    MessageBox.Show("Unknown error in train number", Globals.AppName, MessageBoxButton.OK, MessageBoxImage.Exclamation);
                }
                train = (from t in Globals.ctx.Trains where t.Number == trainNumber select t).FirstOrDefault<Train>();
            }
            else train = null;
            
            try
            {
                if (isUpdating)
                {
                    Passenger passenger = lvPassengers.SelectedItem as Passenger;
                    if (passenger == null) return; // should never happen - internal error
                    passenger.Name = name;
                    passenger.Gender = gender;
                    passenger.Train = train;
                    Globals.ctx.SaveChanges();
                }
                else
                { // adding
                    Passenger passenger = new Passenger() { Name = name, Gender = gender, Train = train};
                    Globals.ctx.Passengers.Add(passenger);
                    train.passCollection.Add(passenger);
                    Globals.ctx.SaveChanges();
                }
                lblId.Content = "-";
                tbName.Text = "";
                rbFemale.IsChecked=false;
                rbMale.IsChecked = false;
                rbNA.IsChecked = true;
                lvPassengers.ItemsSource = (from f in Globals.ctx.Passengers select f).ToList<Passenger>();
            }
            catch (DataException ex)
            {
                MessageBox.Show("Database error:\n" + ex.Message, Globals.AppName, MessageBoxButton.OK, MessageBoxImage.Error);
            }
            catch (SystemException ex)
            {
                MessageBox.Show("Database error:\n" + ex.Message, Globals.AppName, MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

        private void DeletePassenger_ButtonClick(object sender, RoutedEventArgs e)
        {
            Passenger passenger = lvPassengers.SelectedItem as Passenger;
            if (passenger == null) return; // should never happen
            MessageBoxResult result = MessageBox.Show("Are you sure you want to delete this record?\n" + passenger, Globals.AppName, MessageBoxButton.OKCancel, MessageBoxImage.Question, MessageBoxResult.Cancel);
            if (result == MessageBoxResult.OK)
            {
                try
                {
                    Globals.ctx.Passengers.Remove(passenger); // schedule for deletion
                    Globals.ctx.SaveChanges();
                    lvPassengers.ItemsSource = (from f in Globals.ctx.Passengers select f).ToList<Passenger>();
                }
                catch (DataException ex)
                {
                    MessageBox.Show("Database error:\n" + ex.Message, Globals.AppName, MessageBoxButton.OK, MessageBoxImage.Error);
                }
                catch (SystemException ex)
                {
                    MessageBox.Show("Database error:\n" + ex.Message, Globals.AppName, MessageBoxButton.OK, MessageBoxImage.Error);
                }
            }
        }

        private void LvPassengers_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            Passenger passenger = lvPassengers.SelectedItem as Passenger;
            if (passenger == null)
            {
                // disable update and delete buttons
                btUpdatePassenger.IsEnabled = false;
                btDeletePassenger.IsEnabled = false;
                return;
            }
            // enable update and delete buttons, load data
            btUpdatePassenger.IsEnabled = true;
            btDeletePassenger.IsEnabled = true;
            lblId.Content = passenger.Id;
            tbName.Text = passenger.Name;
            if (passenger.Gender == Gender.Female)
            {
                rbFemale.IsChecked = true;
            }
            else if (passenger.Gender == Gender.Male)
            {
                rbMale.IsChecked = true;
            }
            else
            {
                rbNA.IsChecked = true;
            }

            cmbTrain.SelectedItem = passenger.Train.Number.ToString();
        }

        private void Return_ButtonClick(object sender, RoutedEventArgs e)
        {
            DialogResult = true;
        }
    }
}