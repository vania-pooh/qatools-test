include_recipe 'chef-selenium::default'
package "xvfb"

directory node['selenium']['server']['installpath']

remote_file File.join(node['selenium']['server']['installpath'], 'selenium-server-standalone.jar') do
  source "http://selenium.googlecode.com/files/selenium-server-standalone-#{node['selenium']['server']['version']}.jar"
  action :create
  mode 0644
end

template "/etc/init/selenium-standalone.conf" do
  source "selenium-standalone.erb"
  mode 0644
  variables ({
    :fbsize => "#{node['selenium']['xvfb']['fbsize']}",
    :xmx => "#{node['selenium']['server']['memory']}",
    :selenium => File.join(node['selenium']['server']['installpath'], 'selenium-server-standalone.jar'),
    :port => "#{node['selenium']['server']['port']}",
    :options => "#{node['selenium']['server']['options']}",
    :log => File.join(node['selenium']['server']['logpath'], 'server.log')})
end

service "selenium-standalone" do
  provider Chef::Provider::Service::Upstart
  supports :restart => true, :start => true, :stop => true
  action [:enable, :start]
end
