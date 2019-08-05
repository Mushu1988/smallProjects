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

namespace Day09CustomDialog
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        

        public MainWindow()
        {
            InitializeComponent();
            Globals.friendsList.Add(new Friend() { Name = "Jerry", Age = 33 });
            Globals.friendsList.Add(new Friend() { Name = "Tomas", Age = 27 });
            Globals.friendsList.Add(new Friend() { Name = "Lisa", Age = 25 });
            lvFriends.ItemsSource = Globals.friendsList;
        }

        private void AddFriend_MenuClick(object sender, RoutedEventArgs e)
        {
            AddEditDialog dialog = new AddEditDialog(this);
           
            if (dialog.ShowDialog() == true)
            {
                lvFriends.Items.Refresh();
            }
        }

        private void lvFriends_MouseDoubleClick(object sender, MouseButtonEventArgs e)
        {
            Friend f = lvFriends.SelectedItem as Friend;
            if (f == null) return;

            AddEditDialog dialog = new AddEditDialog(this, f);

            if (dialog.ShowDialog() == true)
            {
                lvFriends.Items.Refresh();
            }
        }
    }
}
