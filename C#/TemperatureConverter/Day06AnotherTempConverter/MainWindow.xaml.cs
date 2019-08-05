using System;
using System.Collections.Generic;
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

namespace Day06AnotherTempConverter
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
        }

        bool isLoaded = false;


        private void TbInput_TextChanged(object sender, TextChangedEventArgs e)
        {
            radioButtons_CheckedChange(sender, e);
        }

        private void radioButtons_CheckedChange(object sender, RoutedEventArgs e)
        {
            int method = 0;
            if (isLoaded == true)
            {
                if (!double.TryParse(tbInput.Text, out double number))
                {
                    MessageBox.Show("Error: ivalid input. Please, enter a valid double.", "Temp converter", MessageBoxButton.OK, MessageBoxImage.Error);
                    return;
                }
                if (inCel.IsChecked == true && outCel.IsChecked == true)
                {
                    method = 1;
                }
                else if (inCel.IsChecked == true && outFahr.IsChecked == true)
                {
                    method = 2;
                }
                else if (inCel.IsChecked == true && outKel.IsChecked == true)
                {
                    method = 3;
                }
                else if (inFahr.IsChecked == true && outCel.IsChecked == true)
                {
                    method = 4;
                }
                else if (inFahr.IsChecked == true && outFahr.IsChecked == true)
                {
                    method = 5;
                }
                else if (inFahr.IsChecked == true && outKel.IsChecked == true)
                {
                    method = 6;
                }
                else if (inKel.IsChecked == true && outCel.IsChecked == true)
                {
                    method = 7;
                }
                else if (inKel.IsChecked == true && outFahr.IsChecked == true)
                {
                    method = 8;
                }
                else if (inKel.IsChecked == true && outKel.IsChecked == true)
                {
                    method = 9;
                }
                else
                {
                    MessageBox.Show("Internal error", "Temp converter", MessageBoxButton.OK, MessageBoxImage.Error);
                    return;
                }

                switch (method)
                {
                    case 1:
                        lblResult.Content = $"{number:f2}";
                        break;
                    case 2:
                        lblResult.Content = $"{(number * 1.8 + 32):f2}";
                        break;
                    case 3:
                        lblResult.Content = $"{(number + 273.15):f2}";
                        break;
                    case 4:
                        lblResult.Content = $"{((number - 32) / 1.8):f2}";
                        break;
                    case 5:
                        lblResult.Content = $"{number:f2}";
                        break;
                    case 6:
                        lblResult.Content = $"{((number + 459.67) * 5 / 9):f2}";
                        break;
                    case 7:
                        lblResult.Content = $"{(number - 273.15):f2}";
                        break;
                    case 8:
                        lblResult.Content = $"{(number * 9 / 5 - 459.67):f2}";
                        break;
                    case 9:
                        lblResult.Content = $"{number:f2}";
                        break;
                    default:
                        MessageBox.Show("Internal error", "Temp converter", MessageBoxButton.OK, MessageBoxImage.Error);
                        break;
                }
            }
        }

        private void Window_Loaded(object sender, RoutedEventArgs e)
        {
            isLoaded = true;
        }
    }
}
