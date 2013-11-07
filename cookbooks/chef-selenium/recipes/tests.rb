package "openjdk-7-jdk" do
  options "--no-install-recommends"
  action :install
end

package "maven" do
  options "--no-install-recommends"
  action :install
end
