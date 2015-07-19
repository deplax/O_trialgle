/*global module:false*/
module.exports = function(grunt) {

    // Project configuration.
    grunt.initConfig({
        // Metadata.
        pkg: grunt.file.readJSON('package.json'),
        //vars
        dirs: { 
            img    : 'img'
        },
        config: {
            bowerFile   : '_bower',
            encoding    : 'UTF-8',
            livePort    : 35729,
            projectName : 'march4'
        },

        // Task configuration.
        clean: {
            all: [
                '**/_bower*.*',
                '<%= dirs.jsp %>/**/*.jsp',
                '<%= dirs.src %>/<%= dirs.cssLib %>/**/*',
                '<%= dirs.dist %>/<%= dirs.css %>/**/*',
                '<%= dirs.dist %>/<%= dirs.js %>/**/*'
            ]
        },
        watch: {
            options: {
              spawn: false
            },

            img: {
                files: '<%= dirs.img %>/*',
                tasks: [],
                options : {
                    livereload: true,
                }
            }
        }
    });

    // These plugins provide necessary tasks.
    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-qunit');
    grunt.loadNpmTasks('grunt-contrib-jshint');
    grunt.loadNpmTasks('grunt-contrib-watch');

    // Default task.
    grunt.registerTask('default', [
      'jshint',
      'qunit',
      'concat',
      'uglify'
    ]);
    grunt.registerTask('reflash', [
      //none
    ]);
};
