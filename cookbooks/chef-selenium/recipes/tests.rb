package "openjdk-7-jdk" do
  options "--no-install-recommends"
  action :install
end

package "maven" do
  options "--no-install-recommends"
  action :install
end


template "/vagrant/browsertest/src/test/resources/tested.browsers" do
  source "tested.browsers.erb"
  mode 0644
  variables ({
   :ffversion => "#{node['selenium']['firefox']['version']}",
   :chromeversion => "#{node['selenium']['chrome']['version']}"
  })
end
