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
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace Day13TravelsEF
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        Train selectedTrain;

        public MainWindow()
        {
            try
            {
                InitializeComponent();
                Globals.ctx = new TravelDbContext();
                lvTrains.ItemsSource = (from t in Globals.ctx.Trains select t).ToList<Train>();         
            }
            catch (Exception ex)
            {
                MessageBox.Show("Fatal error: " + ex.Message, Globals.AppName, MessageBoxButton.OK, MessageBoxImage.Error);
                Close();
            }
        }

        private void AddTrain_MenuClick(object sender, RoutedEventArgs e)
        {
            AddEditTrainDialog dialog = new AddEditTrainDialog(this, selectedTrain);
            if (dialog.ShowDialog() == true)
            {
                lvTrains.ItemsSource = (from t in Globals.ctx.Trains select t).ToList<Train>();
            }
        }

        private void DeleteTrain_ButtonClick(object sender, RoutedEventArgs e)
        {
            if (selectedTrain == null) return; 
            MessageBoxResult result = MessageBox.Show("Are you sure you want to delete this record?\n" + selectedTrain, Globals.AppName, MessageBoxButton.OKCancel, MessageBoxImage.Question, MessageBoxResult.Cancel);
            if (result == MessageBoxResult.OK)
            {
                try
                {
                    Globals.ctx.Trains.Remove(selectedTrain); 
                    Globals.ctx.SaveChanges();
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
            lvTrains.ItemsSource = (from t in Globals.ctx.Trains select t).ToList<Train>();
        }

        private void LvTrains_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            selectedTrain = lvTrains.SelectedItem as Train;
        }

        private void ManagePassengersDialog_ButtonClick(object sender, RoutedEventArgs e)
        {
            ManagePassengersDialog dialog = new ManagePassengersDialog(this);
            if (dialog.ShowDialog() == true)
            {
                lvTrains.ItemsSource = (from t in Globals.ctx.Trains select t).ToList<Train>();
            }
        }
    }
}
