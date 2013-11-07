include_recipe "chef-selenium::default"

package "google-chrome-stable" do
  options "--force-yes"
  action :install
end

execute "unpack chromedriver" do
  command "unzip -o /tmp/chromedriver_linux32.zip -d #{node['selenium']['chromedriver']['installpath']}"
  action :nothing
end

remote_file "/tmp/chromedriver_linux32.zip" do
  source "http://chromedriver.storage.googleapis.com/2.6/chromedriver_linux32.zip"
  action :create
  notifies :run, "execute[unpack chromedriver]", :immediately
end

file File.join(node['selenium']['chromedriver']['installpath'], 'chromedriver') do
  mode 0755
  owner 'root'
  group 'root'
end
